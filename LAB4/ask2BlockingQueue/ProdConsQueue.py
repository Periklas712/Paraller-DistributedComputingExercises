import multiprocessing
import time

def producer(queue,iterations,scale):
   for i in range(iterations):
        print("Produced: ",i)
        queue.put(i)
        time.sleep(scale)

def consumer(queue,scale):
   while True:
        i = queue.get()
        print("Consumed: ",i)
        time.sleep(scale)   


if __name__ == '__main__':
    scale = 0.1 
    iterations=20
    queue = multiprocessing.Queue(maxsize=10)

    producer_thread = multiprocessing.Process(target=producer,args=(queue,iterations,scale))
    consumer_thread = multiprocessing.Process(target=consumer,args=(queue,scale))

    producer_thread.start()
    consumer_thread.start()
