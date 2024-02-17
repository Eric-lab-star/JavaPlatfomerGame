package utils

import (
	"encoding/json"
	"fmt"
	"io/fs"
	"log"
	"os"
	"strings"
	"time"
)

type FileStatStruct struct {
	Name     string
	Size     int64
	ModeTime time.Time
}

func HandleFile(fileStats *[]FileStatStruct) fs.WalkDirFunc {

	return func(path string, d fs.DirEntry, err error) error {
		err = skipGit(d.Name())
		if err == fs.SkipDir {
			return err
		}

		if strings.Contains(path, ".java") {
			fmt.Println(path)
			SaveFileStat(path, fileStats)
		}
		return nil
	}
}

func skipGit(dir string) error {
	switch dir {
	case ".git":
		return fs.SkipDir
	}
	return nil
}

func SaveFileStat(path string, fileStats *[]FileStatStruct) {

	fileStat, err := os.Stat(path)
	if err != nil {
		fmt.Printf("function saveFileSat :%v\n", err)
		os.Exit(1)
	}

	data := FileStatStruct{Name: fileStat.Name(), Size: fileStat.Size(), ModeTime: fileStat.ModTime()}

	*fileStats = append(*fileStats, data)
}

func WriteFileStat(fileStats []FileStatStruct) {

	jsonData, err := json.Marshal(fileStats)
	if err != nil {
		log.Fatal(err)
	}

	err = os.WriteFile("./fileInfo.json", jsonData, 0644)
	if err != nil {
		log.Fatal(err)
	}

}
