public class Test
{
    public static void main(String[] args)
    {
        List<Character> test = new List<>();
        test.add(0, 'a');
        test.add(0, 'a');
        test.add(0, 'a');
        test.add(0, 'b');

        System.out.println(test.lastIndexOf('a'));
    }
}
