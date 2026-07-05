import rpyc
from rpyc.utils.server import ThreadedServer

class MyCalculator(rpyc.Service):

    def exposed_add(self,a,b):
        return a+b
    
    def exposed_sub(self,a,b):
        return a-b
    
    def exposed_mul(self,a,b):
        return a*b
    
    def exposed_div(self,a,b):
        return a/b
    
if __name__ == "__main__":
    server =ThreadedServer(MyCalculator,port =18812)
    server.start()

    

    