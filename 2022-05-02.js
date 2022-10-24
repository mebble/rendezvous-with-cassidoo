const dict = ['apple', 'banana', 'cranberry', 'strawberry'];

function simpleAutocomplete(query) {
    return dict.filter(word => word.includes(query));
}

console.log(simpleAutocomplete('app'))
console.log(simpleAutocomplete('berry'))
console.log(simpleAutocomplete('fart'))
