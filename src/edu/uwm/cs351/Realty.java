package edu.uwm.cs351;

/**
 * List of properties for sale.
 * The properties are sorted by price.
 */
public class Realty {

        public static class Listing {
                private final int price;
                private final String address;
                
                public static final int CEILING = 2_000_000_000;
                
                /**
                 * Create a Realty Listing with the given price and location.
                 * @param price a non-negative integer less than {@link #CEILING}
                 * @param address arbitrary string describing the location (cannot be null)
                 * @throws IllegalArgumentException if price is negative
                 */
                public Listing(int price, String address) {
                        if (price < 0) throw new IllegalArgumentException("Price cannot be negative");
                        if (price >= CEILING) throw new IllegalArgumentException("Price cannot be $2B or more");
                        if (address == null) throw new NullPointerException("Address cannot be null");
                        this.price = price;
                        this.address = address;
                }
                
                public int getPrice() { return price; }
                public String getAddress() { return address; }
                
                @Override
                public boolean equals(Object o) {
                        if (o instanceof Listing) {
                                Listing l = (Listing)o;
                                return price == l.price && address.equals(l.address);
                        } else return false;
                }
                
                @Override
                public int hashCode() {
                        return price + address.hashCode();
                }
                
                public String toString() {
                        return "$" + price + ":" + address;
                }
                
                /**
                 * Convert a string of the format $N:... into a {@link Listing}.
                 * @param s string to use (must not be null)
                 * @return a Listing (never null)
                 * @throws NumberFormatException
                 * If the string doesn't start with a dollar sign, or if there is no colon,
                 * or if the string between the dollar sign and the (first) colon is not a valid
                 * integer
                 * @throws IllegalArgumentException if N is not under the {@link CEILING}.
                 */
                public static Listing fromString(String s) throws NumberFormatException {
                        int colon = s.indexOf(':');
                        if (colon < 0) throw new NumberFormatException("Can't find end of price");
                        if (s.charAt(0) != '$') throw new NumberFormatException("Price must be in US dollars.");
                        return new Listing(Integer.parseInt(s.substring(1, colon)),s.substring(colon+1));
                }
        }
        
        private static class Node {
                Listing entry;
                Node left, right;
                Node (Listing r) { entry = r; }
        }
        
        private Node root;
        private int numListings;
        
        private static boolean doReport = true;
        
        /**
         * Used to report an error found when checking the invariant.
         * @param error string to print to report the exact error found
         * @return false always
         */
        private boolean report(String error) {
                if (doReport) System.out.println("Invariant error found: " + error);
                return false;
        }

        private int reportNeg(String error) {
                report(error);
                return -1;
        }
        
        /**
         * Check that all listings in the subtree are in range.
         * Report any errors.  If there is an error return a negative number.
         * (Write "return reportNeg(...);" when detecting a problem.)
         * Otherwise return the number of nodes in the subtree.
         * Note that the range should be updated when doing recursive calls.
         * @param lo all prices in the subtree rooted at r must be greater than this
         * @param hi all prices in the subtree rooted at r must be less than this
         * @return number of nodes in the subtree
         */
        private int checkInRange(Node r, int lo, int hi) { 
                if (r == null) return 0;
                if (r.entry == null) return reportNeg("null in tree");
                int price = r.entry.getPrice();
                if (price <= lo) {
                        return reportNeg("Entry " + r.entry + " less than " + lo);
                }
                if (price >= hi) {
                        return reportNeg("Entry " + r.entry + " greater than " + hi);
                }
                int n1 = checkInRange(r.left,lo,price);
                int n2 = checkInRange(r.right,price,hi);
                if (n1 < 0 || n2 < 0) return -1; // already reported
                return n1 + n2 + 1;
        }

        /**
         * Check the invariant.  
         * Returns false if any problem is found.  It uses
         * {@link #report(String)} to report any problem.
         * @return whether invariant is currently true.
         */
        private boolean wellFormed() {
                int n = checkInRange(root,-1,Listing.CEILING);
                if (n < 0) return false; // problem already reported
                if (n != numListings) return report("numListings is " + numListings + " but should be " + n);
                return true;
        }
        
        /**
         * Create an empty set of listings.
         */
        public Realty() {
                root = null;
                numListings = 0;
                assert wellFormed() : "invariant false at end of constructor";
        }
        
        
        /// Accessors
        
        public int size() {
                assert wellFormed() : "invariant false at start of size()";
                return numListings;
        }
        
        /**
         * Return the lowest price realty listing.
         * @return the lowest price realty listing or null if none at all
         */
        public Listing getMin() {
                assert wellFormed() : "invariant false at start of getMin()";
                if (root == null) return null;
                Node n = root;
                while (n.left != null) n = n.left;
                return n.entry;
        }
        
        private Listing getNextSubtree(Node r, int floor, Listing best) {
                if (r == null) return best;
                if (r.entry.price > floor) return getNextSubtree(r.left,floor,r.entry);
                else return getNextSubtree(r.right,floor,best);
        }
        
        /**
         * Get the next realty listing MORE than the given price.
         * @param floor realty entry must be higher than this price.
         * @return listing with next higher price, or null if no more.
         */
        public Listing getNext(int floor) {
                assert wellFormed() : "invariant false at start of getNext()";
                return getNextSubtree(root, floor, null); // call recursive helper
        }
        
        
        /// Mutators
        
        private void placeUnder(Node n, int price, Node lag) {
                if (lag == null) {
                        root = n;
                } else if (price >= lag.entry.getPrice()) {
                        lag.right = n;
                } else {
                        lag.left = n;
                } 
        }

        /**
         * Add a new listing to the realty unless some other listing
         * already is listed at the same price, in which case return false.
         * @param l listing to add (must not be null)
         * @return true if the listing was added.
         * @throws NullPointerException if the listing is null
         */
        public boolean add(Listing l) {
                assert wellFormed() : "invariant false at start of add()";
                boolean result = false;
                Node n = root;
                Node lag = null;
                while (n != null) {
                        int price = n.entry.price;
                        if (price == l.price) break;
                        lag = n;
                        if (l.getPrice() > price) n = n.right;
                        else n = n.left;
                }
                if (n == null) {
                        n = new Node(l);
                        placeUnder(n, l.getPrice(), lag);
                        ++numListings;
                        result = true;
                }
                assert wellFormed() : "invariant false at end of add";
                return result;
        }

        /**
         * Add all the listings in the array into this Realty from the range [lo,hi).
         * The elements are added recursively from the middle, so that
         * if the array was sorted, the tree will be balanced.
         * All the tree mutations should be done by add.
         * The array should not include any nulls in it.
         * Return number of listings actually added; some might not be added
         * because they duplicate a price in a previously added listing.
         * @param array source
         * @param lo lower bound (usually 0)
         * @param hi upper bound (using array.length), must be >= lo
         * @return number of entries added
         */
        public int addAll(Listing[] array, int lo, int hi) {
                if (lo >= hi) return 0;
                int mid = (lo + (hi - lo)/2);
                int n1 = add(array[mid]) ? 1 : 0;
                int n2 = addAll(array,lo,mid);
                int n3 = addAll(array,mid+1,hi);
                return n1 + n2 + n3;
        }
                
        /**
         * Copy all the listings (in sorted order) into the array
         * at the given index.  Return the next index for (later) elements.
         * This is a helper method for {@link #toArray(Listing[])}.
         * @param array destination of copy
         * @param r the subtree whose elements should be copied
         * @param index the index to place the next element
         * @return the next spot in the array to use after this subtree is done
         */
        private int copyInto(Listing[] array, Node r, int index) {
                if (r == null) return index;
                index = copyInto(array,r.left,index);
                array[index++] = r.entry;
                return copyInto(array,r.right,index);
        }
        
        /**
         * Return an array of all the listings in order.
         * @param array to use unless null or too small
         * @return array copied into
         */
        public Listing[] toArray(Listing[] array) {
                assert wellFormed() : "invariant false at the start of toArray()";
                if (array == null || array.length < size()) array = new Listing[numListings];
                copyInto(array,root,0);
                return array;
        }

        /**
         * Rebalance the tree. No elements should change.
         */
        public void balance() {
        	// TODO
        }
}