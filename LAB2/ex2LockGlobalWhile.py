import multiprocessing

#edo dhlono tis global metablites
end = 10000
num_processes = 4

#leitoyrgia toy nhmatos
def worker(counter, array, end,shared_lock):
    while True:
        #edo bazo to lock sto krisimo tmima
        with shared_lock:
            if counter.value >= end:
                break
            array[counter.value] += 1
            counter.value += 1  
          

def check_array(array):
    errors = 0
    print("Checking...")

    for i in range(end):
        if array[i] != 1:
            errors += 1
            print(f"{i}: {array[i]} should be 1")

    print(f"{errors} errors.")


if __name__ == "__main__":
    counter = multiprocessing.Value('i', 0) 
    array = multiprocessing.Array('i', [0] * end)  
    shared_lock = multiprocessing.Lock()

    processes = [multiprocessing.Process(target=worker, args=(counter, array, end,shared_lock))for _ in range(num_processes)]

    for process in processes:
        process.start()

    for process in processes:
        process.join()

    check_array(array)


    
