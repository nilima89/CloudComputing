Clean up and see if all Jps are running.
Run the code using the following command.
1. cd /root/MoocHomeworks/HadoopPageRank/
2. /compileAndExecHadoopPageRank . sh PageRankDataGenerator/pagerank5000g50 . input .0 5000 1
# usage: ./compileAndExecHadoopPageRank.sh [PageRank Input File ][Number of Urls ][Number OfIterations ]

We have changed the compileAndExecHadoopPageRank.sh file with sleep time to 20 seconds.

See the output in the following directory.
cd /root/MoocHomeworks/HadoopPageRank/output$ cat output/∗

We have two output files here.
1. part_r_00000 (Page rank of all 5000 websites.)
2. nsahoo_HadoopPageRank_output.txt (First 10 ranked websites)