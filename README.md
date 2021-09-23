# ITC313A2Task3
Task 3: Tax Calculator (15 marks)

Write a Java GUI program that will calculate tax for a taxable income. The taxrates.txt file contains the tax rates which will be uploaded in the subject interact site. The program needs to read the taxrates.txt file and store the tax information in proper data structure. The format of the taxrates.txt file is as follows:

Taxable Income            Tax on Income

0 – $18,200                      0

$18,201 – $45,000         9c for each $1 over $18,200

$45,001 – $120,000      $5,092 plus 32.5 cents for each $1 over $45,000

$120,001 – $180,000    $29,467 plus 37 cents for each $1 over $120,000

$180,001 and over       $51,667 plus 45 cents for each $1 over $180,000



The program takes user input on taxable income as shown above. The tax will be shown in the textbook next to the label Tax when "Calculate" button is clicked on as shown below.



The tax rate stored in the taxrates.txt file will be used to calculate tax for a particular taxable income.

For example, when a taxable income is 160000 then tax would be 29467 + (160000 - 120000)*0.37 = 29467+14800 = 44267. The total tax 44267.00 for taxable income 160000 is shown in the above screenshot.
