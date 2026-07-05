import multiprocessing
import math
import random


def merge(arr, l, m, r):
    n1 = m - l + 1
    n2 = r - m
    
    # Create temp arrays
    L = [0] * n1
    R = [0] * n2
    
    # Copy data to temp arrays L[] and R[]
    for i in range(n1):
        L[i] = arr[l + i]
    
    for j in range(n2):
        R[j] = arr[m + 1 + j]
    
    # Merge the temp arrays back into arr[l..r]
    i = 0     # Initial index of first subarray
    j = 0     # Initial index of second subarray
    k = l     # Initial index of merged subarray
    
    while i < n1 and j < n2:
        if L[i] <= R[j]:
            arr[k] = L[i]
            i += 1
        else:
            arr[k] = R[j]
            j += 1
        k += 1
    
    # Copy the remaining elements of L[], if there are any
    while i < n1:
        arr[k] = L[i]
        i += 1
        k += 1
    
    # Copy the remaining elements of R[], if there are any
    while j < n2:
        arr[k] = R[j]
        j += 1
        k += 1

def merge_sort(arr, left, right, current_depth, max_depth):
    if left < right:
        mid = left + (right - left) // 2
        if current_depth < max_depth:
          
            p1 = multiprocessing.Process(target=merge_sort, args=(arr, left, mid, current_depth + 1, max_depth))
            p2 = multiprocessing.Process(target=merge_sort, args=(arr, mid + 1, right, current_depth + 1, max_depth))
            p1.start()
            p2.start()
            p1.join()
            p2.join()
        else:
            merge_sort(arr, left, mid, current_depth + 1, max_depth)
            merge_sort(arr, mid + 1, right, current_depth + 1, max_depth)

        merge(arr, left, mid, right)

# Function to print the array
def print_array(arr):
    for i in range(len(arr)):
        print(f"{arr[i]}", end=" ")
    print()

# Driver code
if __name__ == "__main__":
    size = 100
    arr = multiprocessing.Array('i', [random.randint(0, 99) for _ in range(size)])

    print("Given array is:")
    print_array(arr)
    
    threads=multiprocessing.cpu_count()
    maxDepth=int(math.log2(threads))

    p = multiprocessing.Process(target=merge_sort, args=(arr, 0, size - 1, 0, maxDepth))
    p.start()
    p.join()
    
    print("\nSorted array is:")
    print_array(arr)
