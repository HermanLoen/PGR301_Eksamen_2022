terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "4.40.0"
    }
  }
  backend "s3" {
    bucket = "analytics-1051"
    key    = "1051/app-runner-exam.tfstate"
    region = "eu-west-1"
  }
}