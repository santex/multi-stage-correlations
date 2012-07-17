#!/bin/sh
echo "convert UTF to ASCII files"
echo "run me to update property files"  


for filename in *utf8
# Traverse list of files ending with 1st argument.
do

stringZ=$filename
newstring=${stringZ/_utf8/}
echo 'convert' $filename '-->' ${newstring} 


native2ascii  ${filename}  > ${newstring}
done

rm *~

echo 'convertion is done '
exit 0
  

