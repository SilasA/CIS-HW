using System;

namespace ConsoleApp1
{
    class Program
    {

        static void Main(string[] args)
        {
            if (args.Length < 3) return;
            if (args[1] != "0" && args[1] != "1") return;
            if (args[2] != "0" && args[2] != "1") return;

            args[0] = args[0].ToLower();

            if (args[0] == "and")
            {
                Console.WriteLine(args[1] + " AND " + args[2] + " is " + (args[1] == "1" && args[2] == "1"));
            }
            else if (args[0] == "or")
            {
                Console.WriteLine(args[1] + " OR " + args[2] + " is " + (args[1] == "1" || args[2] == "1"));
            }
            else if (args[0] == "xor")
            {
                Console.WriteLine(args[1] + " XOR " + args[2] + " is " + (args[1] == "1" ^ args[2] == "1"));
            }

            Console.ReadKey();
        }
    }
}
