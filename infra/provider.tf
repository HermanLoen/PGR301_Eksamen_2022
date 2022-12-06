terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "4.40.0"
    }
  }
  backend "s3" {
    bucket = "1051-exam-2022-terraform-state"
    key    = "1051/app-runner-exam.tfstate"
    region = "eu-west-1"
  }
}