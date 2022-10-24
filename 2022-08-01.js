const numberOfOnes = n => {
    const ans = range(n + 1)
        .filter(x => x.toString().includes('1'))
        .map(x => Array.from(x.toString()).filter(c => c === '1').length)
        .length;
    return ans;
}

function range(n) {
    return Array.from({ length: n }, (_, i) => i);
}

console.log(numberOfOnes(14))
