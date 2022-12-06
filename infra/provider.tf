terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "4.40.0"
    }
  }
  backend "s3" {
    bucket = "pgr301-exam-terraform-state"
    key    = "1051/app-runner-exam.tfstate"
    region = "eu-west-1"
  }
}