package com.shopcartify.services.interfaces;


import java.util.ArrayList;
import java.util.List;

public class SearchSimulation {
    public static void main(String[] args) {
    List<Product> products = populateList(1);

    List<Product> bruteForceResult = bruteForce(products, 5, 5);
    List<Product> binaryResult = binarySearch(products, 5, 5);

    }


    public static List<Product> binarySearch(List<Product> products, int first, int second) {
      return binarySearchEvenAndOdd(products, first, second);
    }

    public static List<Product> binarySearchEvenAndOdd(List<Product> products, int first, int second) {
        long startTime = System.currentTimeMillis();

        int length = products.size();

        if (length%2 == 0) {
            return evenBinarySearch(products, first, second, startTime, length);
        }
        return oddBinarySearch(products, first, second, startTime, length);

    }

    private static void validateValues( int first, int second, List<Product> foundProducts,Product product) {
        int productFirst = product.getFirst() ;
        int productSecond = product.getSecond() ;

        if (productFirst == first && productSecond == second)
            foundProducts.add(product);
    }
    private static List<Product> oddBinarySearch(List<Product> products, int first, int second , long startTime, int length) {

        int firstStartLocation = 0;
        int middleLocation = (length/2);
        int firstEndLocation = middleLocation-1;
        int secondStartLocation = middleLocation+1;

        List<Product> foundProducts = searchMethod(products, first, second, firstStartLocation, firstEndLocation, secondStartLocation);

        Product middleProduct = products.get(middleLocation);

        validateValues(first,second,foundProducts,middleProduct);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Execution Time for odd binary-search  : " + executionTime + " milliseconds");
        System.out.println("binary search result : "+foundProducts.size()+"\n");
        return foundProducts;
    }

    private static List<Product> evenBinarySearch(List<Product> products, int first, int second, long startTime, int length) {

        int firstStartLocation = 0;
        int middleLocation = (length/2)-1;
        int secondStartLocation = middleLocation+1;


        List<Product> foundProducts = searchMethod(products, first, second, firstStartLocation, middleLocation, secondStartLocation);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Execution Time for even binary-search  : " + executionTime + " milliseconds");
        System.out.println("binary search result : "+foundProducts.size()+"\n");
        return foundProducts;
    }

    private static List<Product> searchMethod(List<Product> products, int first, int second, int firstStartLocation, int firstEndLocation, int secondStartLocation) {
        List<Product> foundProducts = new ArrayList<>();
        int firstEndLocationToMutate = firstEndLocation;
        int secondEndLocationToMutate = products.size() -1;
        int firstMiddleLocation = (firstEndLocation/2);
        int secondMiddleLocation = (secondEndLocationToMutate-firstMiddleLocation);

        int counter = 0;

        while ( firstStartLocation <= firstEndLocation) {

            Product startProductBeginning = products.get(firstStartLocation);
            Product startProductEnding = products.get(firstEndLocationToMutate);
            Product halfProductBeginning = products.get(secondStartLocation);
            Product halfProductEnding = products.get(secondEndLocationToMutate);

            validateValues(first,second, foundProducts, startProductBeginning);
            validateValues(first,second, foundProducts, startProductEnding);

            validateValues(first,second, foundProducts, halfProductBeginning);
            validateValues(first,second, foundProducts, halfProductEnding);

            if (secondEndLocationToMutate == 4)
                        System.out.println("\nIts equal to!!!...\n");

            firstStartLocation++;
            firstEndLocationToMutate--;
            secondStartLocation++;
            secondEndLocationToMutate --;

//            if (firstStartLocation == firstMiddleLocation &&  ){
//                System.out.println("Am at the middle with : "+ firstMiddleLocation);
//                break;
//            }
            counter++;
        }
        System.out.println("counter counted " +counter+" times");
        return foundProducts;
    }

    public static List<Product> bruteForce(List<Product> products, int first, int second){

        long startTime = System.currentTimeMillis();

        List<Product> result = new ArrayList<Product>();
        for (int i = 0; i < products.size()-1; i++) {
            boolean isFound = products.get(i).getFirst() == first && products.get(i).getSecond() == second;
            if (isFound){
                result.add(products.get(i));
            }
        }
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;


        System.out.println("Execution Time for brute-force : " + executionTime + " milliseconds");
        System.out.println("Brute force result is : "+result.size()+"\n");
        return result;
    }
    public static List<Product> populateList(int optional) {
        List<Product> products = new ArrayList<>();

        int firstCount = 0;
        int secondCount = 10;
//        long size =  10_000_000L + optional;
        long size =  9;


        for (long i = 0L; i < size; i++) {
            Product product = new Product();
            product.setFirst(firstCount);
            product.setSecond(secondCount);

            products.add(product);
            firstCount++;
            secondCount--;
            if (firstCount == 11) firstCount = 0;
            if (secondCount == 0) secondCount = 11;
        }
        return products;
    }
    static class Product{
        private int first;
        private int second;

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "Product{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
}
