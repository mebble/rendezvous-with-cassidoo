// https://buttondown.com/cassidoo/archive/work-smart-get-things-done/
// https://x.com/cassidoo/status/1822863723855323419

package main

import (
	"fmt"
	"os"
	"reflect"
	"regexp"
	"strings"
)

const eq = "="

var re = regexp.MustCompile("log\\((\\w+)\\)")

type Variable struct {
	name string
	used bool
}

func update(variables *[]Variable, varName string, used bool) {
	for i, v := range *variables {
		if v.name == varName {
			(*variables)[i] = Variable{varName, used}
			return
		}
	}
	*variables = append(*variables, Variable{varName, used})
}

func findUnused(statements []string) []string {
	var variables []Variable
	for _, s := range statements {
		if strings.Contains(s, eq) {
			parts := strings.Split(s, eq)
			lhs := strings.TrimSpace(parts[0])
			rhs := strings.TrimSpace(parts[1])
			update(&variables, lhs, false)
			update(&variables, rhs, true)
		} else {
			v := re.FindAllStringSubmatch(s, 1)[0][1]
			update(&variables, v, true)
		}
	}

	var unusedVars []string
	for _, varData := range variables {
		if !varData.used {
			unusedVars = append(unusedVars, varData.name)
		}
	}

	return unusedVars
}

func main() {
	assert([]string{"a", "c"}, findUnused([]string{"a = 1", "b = 3", "c = 2", "log(b)"}))
	assert([]string{"c"}, findUnused([]string{"a = 1", "b = a", "c = 2", "log(b)"}))
}

func assert(expected, actual any) {
	if !reflect.DeepEqual(expected, actual) {
		msg := fmt.Sprint("expected ", expected, " but actual is ", actual)
		println(msg)
		os.Exit(1)
	}
}
