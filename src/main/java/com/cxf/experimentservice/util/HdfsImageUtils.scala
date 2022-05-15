package com.cxf.experimentservice.util

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object HdfsImageUtils {
  def gethdfsDataImage(hdfspath: String): Unit ={

    //1.创建上下文环境配置对象
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("root")
    //2.创建sc
    val sc = new SparkContext(conf)
    //2、创建sparksession对象
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    //3.数据处理
    val frame = spark.read.format("image").load( "hdfs://nameservice1/data_yqt/测试图片2.png");


    return frame

  }

  def main(args: Array[String]): Unit = {
    println(gethdfsDataImage("a"))
  }
}
