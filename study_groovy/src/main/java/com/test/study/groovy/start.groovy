//Learn Groovy start
/*learn Groovy start*/
println "hello wrold" // hello

println 1 + 2

def map = [:] // map
map.'key1' = 'val1'
map.'key2' = 'val2'

//println map
//println map.'key1'

def fsname = 'homer'
map."name-${fsname}" = 'homer'
assert map.'name-homer' == 'homer'
println map
// 多行字符串
def aMultilineString = '''\
line 1
line 2
line 3
'''
print aMultilineString
//转义字符
println ' af \\ afe'
//String
def atname='guname'
def greet = "hello ${atname}"
assert greet.toString() == 'hello guname'
assert greet instanceof GString


// slashy string
def abc = /abc/
println  abc
abc = /   one 
      two
  three/
println abc

abc = $/abc
cba
bca/$
println abc


byte  b = 1
char  c = 2
short s = 3
int   i = 4
long  l = 5
println b * c

def mybool = false
print mybool
// list
def arrayList = [1, 2, 3]
assert arrayList instanceof java.util.ArrayList

def linkedList = [2, 3, 4] as LinkedList   
assert linkedList instanceof java.util.LinkedList

LinkedList otherLinked = [3, 4, 5]          
assert otherLinked instanceof java.util.LinkedList 

//arrays
String[] arrStr = ['Ananas', 'Banana', 'Kiwi']
def numArr = [1, 2, 3] as int[]      
println arrStr
println arrStr[1]
println numArr
assert numArr.size() == 3

//maps
def colors = [red: '#FF0000', green: '#00FF00', blue: '#0000FF']
assert colors['red'] == '#FF0000'    
assert colors.green  == '#00FF00'    
assert colors.unknown == null
println 2**3

import static Calendar.getInstance as now

print now().class


trait Greeter {
    private String greetingMessage() {                     
        'Hello from a private method!'
    }
    String greet() {
        def m = greetingMessage()                          
        println m
        m
    }
}
class GreetingMachine implements Greeter {}                
def g = new GreetingMachine()
assert g.greet() == "Hello from a private method!"         
try {
    assert g.greetingMessage()                             
} catch (MissingMethodException e) {
    println "greetingMessage is private in trait"
}



