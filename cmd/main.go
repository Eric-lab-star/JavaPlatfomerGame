package main

import (
	"fmt"

	"github.com/pkg/errors"
)

func A() {
	defer fmt.Println("A")
	defer func() {
		if r := recover(); r != nil {
			fmt.Printf("Panic: %+v\n", r)
		}
	}()
	B()
}
func B() {
	defer fmt.Println("B")
	C()
}
func C() {
	defer fmt.Println("C")
	Break()
}
func Break() {
	defer fmt.Println("D")
	panic(errors.New("the show must go on"))
}
func main() {
	A()
}
