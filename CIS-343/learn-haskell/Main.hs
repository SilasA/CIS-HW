module Main where
import Text.Regex.TDFA

addcalc :: Float -> Float -> Float
addcalc a b = a + b

subcalc :: Float -> Float -> Float
subcalc a b = a - b

mulcalc :: Float -> Float -> Float
mulcalc a b = a * b

divcalc :: Float -> Float -> Float
divcalc a b = a / b

powcalc :: Float -> Int -> Float
powcalc a b = a ^ b 

evaluateImplUnary :: String -> (Float, Bool) -> Float
evaluateImplUnary line (current, empty) = do
    let operands = line =~ "([0-9]*\\.[0-9]+|[0-9]+)" :: String
    let operator = line =~ "[-|+|\\/|*|^]" :: String
    if operator == "+"
        then 
            --putStrLn ((show current) ++ (show (read operands ::Float)))
            addcalc (if empty then 0 else current) (read operands ::Float)
    else if operator == "-"
        then subcalc (if empty then 0 else current) (read operands ::Float)
    else if operator == "*" && (operands =~ "(0*\\.0+|0+)") == False
        then mulcalc (if empty then 1 else current) (read operands ::Float)
    else if operator == "/"
        then divcalc (if empty then 0 else current) (read operands ::Float)
    else if operator == "^"
        then powcalc (if empty then 0 else current) (read operands ::Int)
    else 0.0

evaluateBinary :: String -> Float
evaluateBinary line = do
    let operands = getAllTextMatches (line =~ "([0-9]*\\.[0-9]+|[0-9]+)") :: [String]
    let operator = line =~ "[-|+|\\/|*|^]" :: String
    if operator == "+"
        then addcalc (read (operands!!0) ::Float) (read (operands!!1) ::Float)
    else if operator == "-"
        then subcalc (read (operands!!0) ::Float) (read (operands!!1) ::Float)
    else if operator == "*" && ((operands!!1) =~ "(0*\\.0+|0+)") == False
        then mulcalc (read (operands!!0) ::Float) (read (operands!!1) ::Float)
    else if operator == "/"
        then divcalc (read (operands!!0) ::Float) (read (operands!!1) ::Float)
    else if operator == "^"
        then powcalc (read (operands!!0) ::Float) (read (operands!!1) ::Int)
    else 0.0

parseType :: String -> (Float, Bool) -> Float
parseType line (current, empty) = do
    if line =~ "([0-9]*\\.[0-9]+|[0-9]+)\\s*[-|+|\\/|*|^]\\s*([0-9]*\\.[0-9]+|[0-9]+)" :: Bool
        then evaluateBinary line
    else if line =~ "\\s*[-|+|\\/|*|^]\\s*([0-9]*\\.[0-9]+|[0-9]+)" :: Bool
        then evaluateImplUnary line (current, empty)
    else current

prompt :: String -> IO String
prompt p = do
    putStr p
    getLine

mainloop :: (Float, Bool) -> IO ()
mainloop (current, empty) = do
    line <- prompt ">"
    if line == "q"
        then putStrLn "Quiting"
        else do 
            let result = parseType line (current, empty)
            putStrLn (show result)
            mainloop (result, False)

main :: IO ()
main = do 
    putStrLn ">Calculator that can to +, -, *, /, ^.  type q to quit.\n>Only do 1 operation at a time and include prefixed 0 on decimals"
    mainloop (0.0, True)
