package main

import (
	"fmt"
	"io/fs"
	"log"
	"os"
	"os/exec"
	"strings"
)

func main() {
	wd, err := os.Getwd()
	checkAny(err)
	fileSystem := os.DirFS(wd)
	fs.WalkDir(fileSystem, ".", Compiler)
	cmd := exec.Command("java", "-cp", "build", "main.Main")
	err = cmd.Run()
	if err != nil {
		log.Fatal(err)
	}
}

func checkAny(err error) {
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
		err := compile(path)
		return err
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

func compile(path string) error {
	fmt.Println(path)
	cmd := exec.Command("javac", "-d", "build", "-cp", "src", path)
	err := cmd.Run()
	return err
}
