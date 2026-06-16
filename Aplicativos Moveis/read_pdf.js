const pdfParse = require('pdf-parse').default || require('pdf-parse');
const fs = require('fs');
const data = fs.readFileSync('C:/Users/laboratorio/Desktop/Ciencia-da-Computacao/Aplicativos Moveis/Arquivos TrabFinal_AplicativosMoveis.pdf');
pdfParse(data).then(d => {
  console.log(d.text);
}).catch(e => console.error(e));
