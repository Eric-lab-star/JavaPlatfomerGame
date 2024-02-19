package main

import (
	"flag"
	"fmt"
	"io/fs"
	"os"
	"os/exec"
	"strings"

	"github.com/eric-lab-star/platformerGame/utils"
)

func main() {
	flag.Parse()
	wd, err := os.Getwd()
	if err != nil {
		fmt.Printf("os.Getwd() from main function %v\n", err)
	}

	newStats := []utils.FileStatStruct{}
	fileSystem := os.DirFS(wd)
	initStats := utils.UnmarshalJavaJsonFile()

	err = fs.WalkDir(fileSystem, "src", utils.WalkJavaFile(&newStats, isJsonExist()))
	if err != nil {
		fmt.Printf("walk dir err %v\n", err)
		os.Exit(1)
	}
	utils.WriteFileStat(newStats)

}

func isJsonExist() bool {
	_, err := os.Open("./fileInfo.json")
	if err != nil {
		return false
	}
	return true
}

func compile(path string) error {
	var err error
	fmt.Println(path)
	cmd := exec.Command("javac", "-d", "build", "-cp", "src", path)
	err = cmd.Run()
	return err
}

func isModified(initStats []utils.FileStatStruct, newStats []utils.FileStatStruct) {

	for _, init := range initStats {
		for _, new := range newStats {
			if strings.Compare(init.Id.String(), new.Id.String()) == 0 {
				fmt.Println("file already exists")
			} else {
				fmt.Println("new file")
			}
		}

	}

}
