#!/usr/bin/awk -f 
BEGIN {
	ch=""
	titre=0
}

{
	while (NR<3) {
	next
	}
	#print NR
	if (gsub(";",";",$1)==2) {
		titre=$2
		ch=""
	} else {
		ch=$(ch)+$0
		print $(ch) > "map" titre ".xsb"
	}
}

#cat Xsokoban2.txt | sed 's/ Level/Level/g' > Xsokoban.txt
#cat Original2.txt | sed 's/Level \(.\)$/Level 0\1/g' > Original.txt
