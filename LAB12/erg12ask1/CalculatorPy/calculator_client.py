import rpyc

connection =  rpyc.connect("localhost",18812)

a = int(input("Please give first number: "))
operation= str(input("Please give operation: ( + , - , * , / ): "))
b = a = int(input("Please give second number: "))

if (operation== "+"):
    result = connection.root.add(a,b)
elif (operation=="-"):
    result = connection.root.sub(a,b)
elif (operation=="*"):
    result = connection.root.mul(a,b)
elif (operation=="/"):
    result = connection.root.div(a,b)
else:
    print("Wrong input")

print (f"Result for {a} {operation} {b} : {result}")