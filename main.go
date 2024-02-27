package main

import (
	"fmt"
	"io/fs"
	"log"
	"os"
	"os/exec"
	"strings"
	"time"
)

var (
	fileSystem fs.FS
	root       string
)

func init() {

	root, err := os.Getwd()
	if err != nil {
		log.Fatal(err)
	}

	fileSystem = os.DirFS(root)
}

func main() {

	fmt.Println("watching..")
	fileWatchChan := make(chan string)
	fileSystemWalker(fileWatchChan)

	for {
		select {
		case f := <-fileWatchChan:
			{
				fmt.Println(f + " been changed")
				javacompiler(f)
			}
		}
	}
}

func fileSystemWalker(fileWatchChan chan string) {
	fs.WalkDir(fileSystem, ".", func(path string, d fs.DirEntry, err error) error {
		if strings.HasSuffix(path, ".java") {
			go watcher(fileWatchChan, path)
		}
		return nil
	})
}

func javacompiler(filename string) {
	if strings.HasSuffix(filename, ".java") {
		cmd := exec.Command("javac", "-d", "build", "-cp", "src", filename)
		err := cmd.Run()
		if err != nil {
			fmt.Println("err", err)
		}
	}
}

// sizeAndTime struct  
type sizeAndTime struct {
	size int64
	time time.Time
}

func watcher(w chan string, filename string) {
	init := &sizeAndTime{}
	for {
		time.Sleep(500 * time.Millisecond)
		fileStat := getFileStat(filename)
		if didChange(init, fileStat) {
			init = fileStat
			w <- filename
		}
	}
}

func getFileStat(filename string) *sizeAndTime {

	info, err := fs.Stat(fileSystem, filename)
	if err != nil {
		log.Fatal(err)
	}

	newStat := &sizeAndTime{
		size: info.Size(),
		time: info.ModTime(),
	}
	return newStat
}

func didChange(oldStat *sizeAndTime, newStat *sizeAndTime) bool {
	if oldStat.size != newStat.size || oldStat.time != newStat.time {
		return true
	}
	return false
}
