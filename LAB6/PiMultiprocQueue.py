import multiprocessing 
from time import perf_counter
from math import pi as mathPi

# ΜΕΓΕΘΟΣ ΠΙΝΑΚΑ   |   Ακολουθιακά    |      2    |    16       ΝΗΜΑΤΑ
#     1εκ          |    0.2463        |   0.1951  |  0.4909     ΧΡΟΝΟΣ ΣΕ sec
#     10εκ         |    1.1064        |   0.5516  |  0.8440     ΧΡΟΝΟΣ ΣΕ sec
#     100εκ        |    8.8478        |   5.0703  |  2.3057     ΧΡΟΝΟΣ ΣΕ sec
#     1δις         |    86.053        |   55.6698 |  19.176     ΧΡΟΝΟΣ ΣΕ sec 

def worker(start,stop,step,shared):
    local_sum=0.0
    for i in range(start,stop):
        x = (i + 0.5) * step
        local_sum += 4.0 / (1.0 + x*x)
    shared.put(local_sum ) # ΒΑΖΩ ΤΟ ΤΟΠΙΚΟ ΑΘΡΟΙΣΜΑ ΤΟ ΚΑΘΕ ΝΗΜΑΤΟΣ ΣΤΗΝ ΚΟΙΝΗ ΟΥΡΑ


if __name__ == '__main__':
    numProc=16
    numberOfSteps = 1000000000
    step = 1.0 / numberOfSteps

    #ΧΡΗΣΙΜΟΠΟΙΩ ΑΥΤΗΝ ΤΗΝ ΜΟΙΡΑΖΟΜΕΝΗ ΔΟΜΗ ΓΙΑ ΝΑ ΕΠΙΚΟΙΝΩΝΩΟΥΝ ΟΙ ΔΙΕΡΓΑΣΣΙΕΣ ΜΕ ΣΕ ΑΥΤΗΝ ΤΗΝ ΔΟΜΗ
    #ΥΠΑΡΧΕΙ ΚΑΙ ΤΟ MANAGER
    #manager = multiprocessing.Manager()
    #  return_dict = manager.dict() MOIRAZOMENO LEKSIKO
    shared = multiprocessing.Queue()
    jobs=[]
    
    t1 = perf_counter()

    #ΧΩΡΙΖΩ ΤΟ ΚΟΜΜΑΤΙ ΠΟΥ ΘΑ ΠΑΡΕΙ ΚΑΘΕ ΝΗΜΑ
    block = numberOfSteps//numProc
    for i in range(numProc):
        start=i*block
        stop=start+block
        if i==numberOfSteps-1:
            stop=numberOfSteps
        j=multiprocessing.Process(target=worker,args=(start,stop,step,shared))
        jobs.append(j)
    
    for j in jobs:  
        j.start()
    
    for j in jobs:
        j.join()

    totalSum=0.0
    while not shared.empty():
        totalSum+=shared.get() #ΜΑΖΕΥΩ ΚΑΘΕ ΤΟΠΙΚΟ ΑΘΡΟΙΣΜΑ ΚΑΙ ΤΑ ΠΡΟΣΘΕΤΩ ΣΤΟ ΣΥΝΟΛΙΚΟ ΑΘΡΟΙΣΜΑ 

    pi = totalSum*step 

    t2 = perf_counter()

    print('Sequential program results with {} steps' .format(numberOfSteps))
    print('Computed pi = {}' .format(pi))
    print('Difference between estimated pi and math.pi = {}' .format(abs(pi - mathPi)))
    print('Time to compute = {} seconds' .format(t2-t1))

