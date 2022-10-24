function overlapRight(int1, int2) {
    const [_, int1Right] = int1;
    const [int2Left, __] = int2;
    return int1Right >= int2Left;
}

function overlapLeft(int1, int2) {
    const [int1Left, _] = int1;
    const [__, int2Right] = int2;
    return int2Right <= int1Left;
}

function mergeTwoIntervals(int1, int2) {
    const left = Math.min(...[...int1, ...int2]);
    const right = Math.max(...[...int1, ...int2]);
    return [left, right];
}

function mergeCheck(merged, interval) {
    for (const m of merged) {
        console.log(`[COMPARE] [${m}] [${interval}]`);
        if (overlapLeft(m, interval) || overlapRight(m, interval)) {
            merged = merged.map(_m => {
                return _m === m
                    ? mergeTwoIntervals(m, interval)
                    : _m;
            });
            console.log(`[MERGED] [${merged}]`);
            return merged;
        }
    }
    return [...merged, interval];
}

function mergeIntervals(intervals) {
    console.log(`[MERGE] [${intervals}]`);
    return intervals.reduce((merged, interval) => mergeCheck(merged, interval), []);
}

console.log(mergeIntervals([[1,4],[2,6],[8,10],[15,20]]));
console.log(mergeIntervals([[1,2],[2,7]]));
