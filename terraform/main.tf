terraform {
  required_providers {
    aws = {
        source = "hashicorp/aws"
        version = "~> 4.0"
    }
  }
}

provider "aws" {
  alias =  "us-east-1"
  region = "us-east-1"
}

resource "aws_iam_role" "iam_for_ec2" {
  name = "iam_for_ec2"

  assume_role_policy = jsonencode({
    Version: "2012-10-17",
    Statement = [
      {
        Effect    = "Allow",
        Action    = "dynamodb:*"
        Principal = {
          Service = "ec2.amazonaws.com"
        }
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "ec2_policy" {
   role         = aws_iam_role.iam_for_ec2.name
   policy_arn   = "arn:aws:iam::aws:policy/AmazonEC2FullAccess"
}

resource "aws_iam_instance_profile" "ec2_profile" {
  role          = aws_iam_role.iam_for_ec2.name
}

resource "aws_instance" "prd-blocking-application" {
  provider                  = aws.us-east-1
  count                     = 1
  ami                       = "${var.amis["us-east-1"]}"
  instance_type             = "${var.instance_type}"
  key_name                  = "${var.key_name}"
  tags = {
    Name                    = "dev${count.index}"
  }
  vpc_security_group_ids    = [
    "${aws_security_group.ssh_access-1.id}",
    "${aws_security_group.http_access.id}"
  ]

  iam_instance_profile  = aws_iam_instance_profile.ec2_profile.name
  
  provisioner "file" {
    source      = "~/setup-environment"
    destination = "/tmp/setup-environment"
  }

  user_data = "${file("user-data-app.sh")}"
}

resource "aws_instance" "prd-reactive-application" {
  provider                  = aws.us-east-1
  count                     = 1
  ami                       = "${var.amis["us-east-1"]}"
  instance_type             = "${var.instance_type}"
  key_name                  = "${var.key_name}"
  tags = {
      Name                  = "dev${count.index}"
  }
  vpc_security_group_ids    = [
    "${aws_security_group.ssh_access-1.id}",
    "${aws_security_group.http_access.id}"
  ]
  iam_instance_profile  = aws_iam_instance_profile.ec2_profile.name
  
  provisioner "file" {
    source      = "~/setup-environment"
    destination = "/tmp/setup-environment"
  }

  user_data = "${file("user-data-app.sh")}"
}

resource "aws_instance" "prd-appdynamics-db" {
  provider = aws.us-east-1
  count = 1
  ami = "${var.amis["us-east-1"]}"
  instance_type = "${var.instance_type}"
  key_name = "${var.key_name}"
  tags = {
      Name = "dev${count.index}"
  }
  vpc_security_group_ids = ["${aws_security_group.ssh_access-1.id}"]

  provisioner "file" {
    source      = "~/setup-environment"
    destination = "/tmp/setup-environment"
  }

  user_data = "${file("user-data-db.sh")}"
}
