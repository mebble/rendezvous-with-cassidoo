const fromTo = (from: number, to: number) => {
    let i = from - 1;
    return () => {
        if (i < to) {
            i++;
            return i;
        }
        return undefined;
    }
};

const gen = fromTo(5, 7)

console.log(gen())
console.log(gen())
console.log(gen())
console.log(gen())
