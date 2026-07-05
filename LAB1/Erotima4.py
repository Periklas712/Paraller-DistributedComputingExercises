import threading

def worker(num,t):
	"""thread worker function"""
	for i in range (21):
		print(f"Thread {num} : {i} * {num} = {i*num}")
	return


if __name__ =="__main__":

	thread_num = 10
	
	print ('Main creates and starts threads')
	threads = [threading.Thread(target=worker, args=(i, thread_num,)) for i in range(thread_num)]

	for t in threads:
		t.start()

	for t in threads:
	    t.join()

	print('Main finished')


