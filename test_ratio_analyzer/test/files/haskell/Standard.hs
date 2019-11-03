import Bla

-- some comment
fac 0 = 1
fac n = n * fac (n-1)

{-
 - Some block comment
 - Prints fac
 -}
main = print (fac 42)
