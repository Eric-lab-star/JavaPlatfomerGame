package main

import (
	"flag"
	"fmt"
	"io/fs"
	"log"
	"os"
	"os/exec"
	"strings"
	"time"
)

var runF = flag.Bool("Jrun", false, "build or run java file\n ")

func main() {
	flag.Parse()
	wd, err := os.Getwd()
	fatalAny(err)
	fileSystem := os.DirFS(wd)
	fs.WalkDir(fileSystem, ".", Compiler)
	if *runF {
		cmd := exec.Command("java", "-cp", "build", "main.Main")
		cmd.Run()
	}
}

func fatalAny(err error) {
	if err != nil {
		log.Fatal(err)
	}
}

func Compiler(path string, d fs.DirEntry, err error) error {
	err = shouldSkip(d.Name())
	if err == fs.SkipDir {
		return err
	}
	if strings.Contains(path, ".java") {
		saveInfo(path)
		isModified(path)
	}
	return nil
}

func shouldSkip(dir string) error {
	switch dir {
	case ".git":
		return fs.SkipDir
	}
	return nil
}

type fileStatStruct struct {
	Name     string
	Size     int64
	ModeTime time.Time
}

func saveInfo(path string) {
	fileStat, err := os.Stat(path)
	fatalAny(err)
	data := fileStatStruct{Name: fileStat.Name(), Size: fileStat.Size(), ModeTime: fileStat.ModTime()}

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
