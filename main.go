package main

import (
	"flag"
	"fmt"
	"io/fs"
	"log"
	"os"
	"os/exec"

	"github.com/eric-lab-star/platformerGame/utils"
)

func main() {
	flag.Parse()
	wd, err := os.Getwd()
	if err != nil {
		fmt.Printf("os.Getwd() from main function %v\n", err)
	}

	fileSystem := os.DirFS(wd)

	fileStats := []utils.FileStatStruct{}

	err = fs.WalkDir(fileSystem, "src", utils.HandleFile(&fileStats))
	if err != nil {
		fmt.Printf("walk dir err %v\n", err)
		os.Exit(1)
	}

	utils.WriteFileStat(fileStats)

}

func compile(path string) error {
	var err error
	fmt.Println(path)
	cmd := exec.Command("javac", "-d", "build", "-cp", "src", path)
	err = cmd.Run()
	return err
}

func isModified(path string) {
	var err error
	initInfo, err := os.Stat(path)
	if err != nil {
		log.Fatal(err)
	}
	info, err := os.Stat(path)
	if err != nil {
		log.Fatal(err)
	}
	if initInfo.Size() != info.Size() || initInfo.ModTime() != info.ModTime() {
	}
}
