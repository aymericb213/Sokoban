#!/usr/bin/awk -f 
BEGIN {
	ch=""
	titre=0
}

{
	#print NR
	if ($0=="") {
	next
	}
	if (gsub(";",";",$1)==1) {
		titre=$3
		ch=""

	} else {
		ch=$(ch)+$0
		print $(ch) > "map" titre ".xsb"
	}
}

#cat Xsokoban2.txt | sed 's/ Level/Level/g' > Xsokoban.txt
#cat Original2.txt | sed 's/Level \(.\)$/Level 0\1/g' > Original.txt
