import multiprocessing
import multiprocessing.process

end =1000
numThreads = 4


def worker(end,array,shared_locks):
    for i in range(end):
        with shared_locks[i]:
            for j in range(i):
                array[i]+=1

def check_array(array):
    errors=0
    print("Checking...")
    for i in range (end):
        if array[i]!=numThreads*i:
            errors+=1
            print(f"{i}: {array[i]} should be {numThreads*i}")
    print(f"{errors} errors. ")
            


if __name__=="__main__":
    array=multiprocessing.Array("i",[0]*end)
    #o πινακας με τα locks θα τον περασω οσ ορισμα
    shared_locks = [multiprocessing.Lock() for _ in range(end)]

    Threads = [multiprocessing.Process(target=worker,args=(end,array,shared_locks))for _ in range(numThreads)]

    for thread in Threads:
        thread.start()
    for thread in Threads:
        thread.join()
    check_array(array)

