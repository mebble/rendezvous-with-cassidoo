// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/matchAll
// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/padEnd

type Table = {
    head: string[];
    body: string[][];
};
type Parser = (inputTable: string) => Table;
type Formatter = (parser: Parser) => (inputTable: string) => string;

const getColumnWidths = (table: Table): number[] => {
    let widths = table.head.map(cell => cell.length);
    table.body.forEach(row => {
        const lengths = row.map(cell => cell.length);
        widths = lengths.map((l, i) => l > widths[i] ? l : widths[i]);
    })
    return widths;
};

const formatTable = (table: Table, columnWidths: number[]): string => {
    let output = wrap(formatRow(table.head, columnWidths))
    output += wrap(
        table.head
            .map((_, i) => ''.padEnd(columnWidths[i], '-'))
            .join(' | ')
    );
    output += table.body.map(row => wrap(formatRow(row, columnWidths))).join('');
    return output;
};

const formatRow = (row: string[], columnWidths: number[]): string => {
    return row
        .map((cell, i) => cell.padEnd(columnWidths[i], ' '))
        .join(' | ');
};

const wrap = (rowString: string): string => {
    return `| ${rowString} |\n`;
};

const formatter: Formatter = parser => tableString => {
    const table = parser(tableString);
    const widths = getColumnWidths(table);
    return formatTable(table, widths);
};

const parser: Parser = (inputString) => {
    const rowMatcher = /\w*[^\|]*\w+/g;
    const [headString, _, ...bodyStrings] = inputString.trim().split('\n');
    const head = [...headString.matchAll(rowMatcher)]
        .map(match => match.toString().trim());
    const body = bodyStrings
        .map(rowString => [...rowString.matchAll(rowMatcher)]
            .map(match => match.toString().trim())
        );
    return {
        head,
        body
    };
};

const format = formatter(parser);

console.log(format(`
| Syntax | Description foo yeah |
| --- | ----------- |
| Header | Title |
| Paragraph | Text |
| Paragraph | Text |
`));
