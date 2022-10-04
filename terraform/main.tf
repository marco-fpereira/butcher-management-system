terraform {
  required_providers {
    aws = {
        source              = "hashicorp/aws"
        version             = "~> 4.0"
    }
  }
}

provider "aws" {
  alias                     = "us-east-1"
  region                    = "us-east-1"
}

resource "aws_iam_role" "iam_for_ec2" {
  name                      = "iam_for_ec2"

  assume_role_policy = jsonencode({
    Version: "2012-10-17"
    Statement = [
      {
        Effect    = "Allow"
        Action   = "sts:AssumeRole"
        Principal = {
          Service = "ec2.amazonaws.com"
        }
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "ec2_policy" {
   role                     = aws_iam_role.iam_for_ec2.name
   policy_arn               = "arn:aws:iam::aws:policy/AmazonEC2FullAccess"
}

resource "aws_iam_instance_profile" "ec2_profile" {
  role                      = aws_iam_role.iam_for_ec2.name
}

resource "aws_instance" "prd-blocking-application" {
  provider                  = aws.us-east-1
  ami                       = "${var.amis["us-east-1"]}"
  instance_type             = "${var.instance_type}"
  key_name                  = "${var.key_name}"
  subnet_id                 = "${aws_subnet.internet_access_public_subnet.id}"
  associate_public_ip_address = true

  tags = {
    Name                    = "prd-blocking-application"
  }
  vpc_security_group_ids    = [
    "${aws_security_group.ssh_access-1.id}",
    "${aws_security_group.http_access.id}",
    "sg-0be9ad045e4d5b5d1"
  ]

  iam_instance_profile      = aws_iam_instance_profile.ec2_profile.name
  
  provisioner "file" {
    source                  = "./setup-environment"
    destination             = "/tmp/setup-environment"

    connection {
      type                  = "ssh"
      agent                 = false
      private_key           = "${file("~/.ssh/terraform.pem")}"
      user                  = "ubuntu"
      host                  = "${aws_instance.prd-blocking-application.public_ip}"
    }
  }

  user_data                 = "${file("user-data-blocking-app.sh")}"
  depends_on = [aws_security_group.http_access, aws_security_group.ssh_access-1]
}

 resource "aws_instance" "prd-reactive-application" {
  provider                  = aws.us-east-1
  ami                       = "${var.amis["us-east-1"]}"
  instance_type             = "${var.instance_type}"
  key_name                  = "${var.key_name}"
  subnet_id                 = "${aws_subnet.internet_access_public_subnet.id}"
  associate_public_ip_address = true

  tags = {
      Name                  = "prd-reactive-application"
  }
  vpc_security_group_ids    = [
    "${aws_security_group.ssh_access-1.id}",
    "${aws_security_group.http_access.id}",
    "sg-0be9ad045e4d5b5d1"
  ]
  iam_instance_profile      = aws_iam_instance_profile.ec2_profile.name
  
  provisioner "file" {
    source                  = "./setup-environment"
    destination             = "/tmp/setup-environment"

    connection {
      type                  = "ssh"
      agent                 = false
      private_key           = "${file("~/.ssh/terraform.pem")}"
      user                  = "ubuntu"
      host                  = "${aws_instance.prd-reactive-application.public_ip}"
    }
  }

  user_data                 = "${file("user-data-reactive-app.sh")}"
  depends_on = [aws_security_group.http_access, aws_security_group.ssh_access-1]

}

resource "aws_instance" "prd-appdynamics-db" {
  provider                  = aws.us-east-1
  ami                       = "${var.amis["us-east-1"]}"
  instance_type             = "${var.instance_type}"
  key_name                  = "${var.key_name}"
  subnet_id                 = "${aws_subnet.internet_access_public_subnet.id}"
  associate_public_ip_address = true
  tags = {
      Name                  = "prd-appdynamics-db"
  }
  vpc_security_group_ids    = [
    "${aws_security_group.ssh_access-1.id}",
    "${aws_security_group.appdynamics_access.id}",
    "sg-0be9ad045e4d5b5d1"
  ]


   provisioner "file" {
    source                  = "./setup-environment"
    destination             = "/tmp/setup-environment"

    connection {
      type                  = "ssh"
      agent                 = false
      private_key           = "${file("~/.ssh/terraform.pem")}"
      user                  = "ubuntu"
      host                  = "${aws_instance.prd-appdynamics-db.public_ip}"
    }
  }

  user_data                 = "${file("user-data-db.sh")}"
  depends_on = [aws_security_group.appdynamics_access, aws_security_group.ssh_access-1]

}
