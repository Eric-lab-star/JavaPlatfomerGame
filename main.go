package main

import (
	"fmt"
	"os"
	"os/exec"
)

func main() {
	fmt.Println("compiling java files ...")
	cmd := exec.Command("javac", "Hello.java")
	err := cmd.Run()
	if err != nil {
		fmt.Fprintf(os.Stderr, "cmd.Run() error: %v\n", err)
	}

	// fmt.Println("Running java program...")
	// cmd = exec.Command("java", "-cp", "./build", "main.Main")
	// cmd.Run()

}
