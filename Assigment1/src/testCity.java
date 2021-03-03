public class testCity {

    public static void main (String[] args) {
        City city1 = new City("Edmonton", 375, 625);
        City city2 = new City("Toronto", 750, 762);
        System.out.println(city1.getName());
        System.out.println(city1.getX());
        System.out.println(city1.getY());
        city1.setName("Edmonton2");
        city1.setX(1);
        city1.setY(1);
        System.out.println(city1.getName());
        System.out.println(city1.getX());
        System.out.println(city1.getY());
        System.out.println(city1);
        Program test = new Program("cities3.txt", true);
        CompressedArray test1 = new CompressedArray(test.compareDistances());
    }
        
}