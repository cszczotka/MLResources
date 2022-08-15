package com.iilab.sparkml

import org.apache.spark.sql.SparkSession

import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.ml.tuning.{ParamGridBuilder, TrainValidationSplit}
import org.apache.log4j._
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.mllib.linalg.Vectors

object LinearRegressionApp extends App {

  Logger.getLogger("org").setLevel(Level.ERROR)


  val spark = SparkSession.builder()
    .master("local[1]")
    .appName("LinearRegressionDemo")
    .getOrCreate();

  println("SparkContext details:")
  println("APP Name :"+spark.sparkContext.appName);
  println("Deploy Mode :"+spark.sparkContext.deployMode);
  println("Master :"+spark.sparkContext.master);

  val ecommerceDF = spark.read.option("header","true")
    .option("inferSchema","true").format("csv")
    .load("c:\\projects\\opensource\\spark\\customer-database\\Ecommerce Customers.csv")

  val ecommerceDF2 = ecommerceDF.select(ecommerceDF("Yearly Amount Spent").as("label"),
    ecommerceDF("Avg Session Length"), ecommerceDF("Time on App"), ecommerceDF("Time on Website"), ecommerceDF("Length of Membership"))
    .na.drop(Seq("label"))

  var assembler = new VectorAssembler().setInputCols(Array("Avg Session Length","Time on App","Time on Website","Length of Membership")).setOutputCol("features")

  val ecommerceDF3 = assembler.setHandleInvalid("skip").transform(ecommerceDF2).select("label", "features")

  val lr = new LinearRegression()
  val lrModel = lr.fit(ecommerceDF3)
  val trainingSummary = lrModel.summary

  trainingSummary.residuals.show()
  println(s"RMSE: ${trainingSummary.rootMeanSquaredError}")
  println(s"MSE: ${trainingSummary.meanSquaredError}")
  println(s"r2: ${trainingSummary.r2}")

}
