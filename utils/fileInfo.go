package utils

import (
	"encoding/json"
	"fmt"
	"io/fs"
	"log"
	"os"
	"strings"

	"github.com/google/uuid"
)

type FileStatStruct struct {
	Id       uuid.UUID
	Name     string
	Size     int64
	ModeTime string
}

func WalkJavaFile(fileStats *[]FileStatStruct, jsonExist bool) fs.WalkDirFunc {

	if !jsonExist {

		return func(path string, d fs.DirEntry, err error) error {
			err = SkipGit(d.Name())
			if err == fs.SkipDir {
				return err
			}

			if strings.Contains(path, ".java") {
				SaveFileStat(path, fileStats)
			}
			return nil
		}
	}

	oldStats := UnmarshalJavaJsonFile()

	return func(path string, d fs.DirEntry, err error) error {
		err = SkipGit(d.Name())
		if err == fs.SkipDir {
			return err
		}

		if strings.Contains(path, ".java") {
			UpdateFileStat(path, oldStats)
		}
		return nil
	}
}

func SkipGit(dir string) error {
	switch dir {
	case ".git":
		return fs.SkipDir
	}
	return nil
}

func UpdateFileStat(path string, fileStats *[]FileStatStruct) {

	newStat, err := os.Stat(path)
	if err != nil {
		fmt.Printf("function UpdateFileStat error :%v\n", err)
		os.Exit(1)
	}

	time, err := newStat.ModTime().MarshalText()
	if err != nil {
		fmt.Printf("saveFileStat ModeTime : %v\n", err)
		os.Exit(1)
	}

	data := FileStatStruct{Id: uuid.New(), Name: newStat.Name(), Size: newStat.Size(), ModeTime: string(time)}

	*fileStats = append(*fileStats, data)
}

func UnmarshalJavaJsonFile() *[]FileStatStruct {
	file, err := os.ReadFile("./fileInfo.json")
	if err != nil {
		fmt.Printf("function compileModified : %v\n", err)
	}

	fileStats := &[]FileStatStruct{}
	json.Unmarshal(file, fileStats)
	return fileStats

}
func SaveFileStat(path string, fileStats *[]FileStatStruct) {

	fileStat, err := os.Stat(path)
	if err != nil {
		fmt.Printf("function saveFileSat :%v\n", err)
		os.Exit(1)
	}

	time, err := fileStat.ModTime().MarshalText()
	if err != nil {
		fmt.Printf("saveFileStat ModeTime : %v\n", err)
		os.Exit(1)
	}

	data := FileStatStruct{Id: uuid.New(), Name: fileStat.Name(), Size: fileStat.Size(), ModeTime: string(time)}

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
