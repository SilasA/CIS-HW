import sys

snm_mapping = {
        'A' : "255.0.0.0",
        'B' : "255.255.0.0",
        'C' : "255.255.255.0",
        'D' : "", 'E' : ""
}

def default_subnet_mask(ip_class):
    return snm_mapping[ip_class]

def get_ip_class(ip):
    first_octet = int(ip.split('.')[0])

    if (first_octet in range(0, 128)):
        return 'A'
    elif (first_octet in range(128, 192)):
        return 'B'
    elif (first_octet in range(192, 224)):
        return 'C'
    elif (first_octet in range(224, 240)):
        return 'D'
    else:
        return 'E' 

def get_network(ip, mask):
    net = ""
    ip = ip.split('.')
    mask = mask.split('.')
    for i in range(4):
        net += str(int(ip[i]) & int(mask[i]))
        if (i != 3):
            net += '.' 
    return net

def get_first_last(network_ip):
    ip = network_ip.split('.')
    first = ""
    last = ""
    for i in range(4):
        if (ip[i] == '0'):
            first += '0'
            last += '255'
        else:
            first += ip[i]
            last += ip[i]

        if (i != 3):
            first += '.'
            last += '.'
    
    newFirst = ""
    newLast = ""

    for i in range(len(first)):
        if (len(first) - 1 == i):
            newFirst += '1'
        else:
            newFirst += first[i]

    for i in range(len(last)):
        if (len(last) - 1 == i):
            newLast += '4'
        else:
            newLast += last[i] 

    return newFirst, newLast

def get_broadcast(net):
    net = net.split('.')
    for i in range(4):
        if (net[i] == '0'):
            net[i] = '255'
    ret = ""

    for i in range(4):
        ret += net[i]
        if (i != 3):
            ret += '.'

    return ret

# start
if (len(sys.argv) < 2):
    exit()

input_name = sys.argv[1]

try:
    in_file = open(input_name, 'r')
except FileNotFoundError:
    printf("Error: file not found by name: " + str(input_name))
    exit()

with in_file:
    text = in_file.read()

    text = text.split('\n')
    #text = [i.split('.') for i in text]

    for ip in text:
        if (ip == ""):
            continue
        c = get_ip_class(ip)
        mask = default_subnet_mask(c)
        net = get_network(ip, mask)
        first, last = get_first_last(net)
        print("IP: " + ip)
        print("Class: " + c)
        print("Subnet Mask: " + mask)
        print("Network: " + net)
        print("BC: " + get_broadcast(net))
        print("Usable: " + first + " -> " + last)
        print("--------------------------------------------------------------------------")

