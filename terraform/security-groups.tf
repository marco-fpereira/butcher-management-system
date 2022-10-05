resource "aws_security_group" "http_access" {
    provider            = aws.us-east-1
    name                = "http-access"
    description         = "Allow http access"
    vpc_id              = "vpc-03ca1ab2a269d2df6"#"${aws_vpc.internet_access_vpc.id}"

    ingress {
        description      = "HTTP access to instances"
        from_port        = 80
        to_port          = 80
        protocol         = "tcp"
        cidr_blocks      = ["0.0.0.0/0"]
    }

    ingress {
        description      = "HTTPS access to instances"
        from_port        = 443
        to_port          = 443
        protocol         = "tcp"
        cidr_blocks      = ["0.0.0.0/0"]
    }

    ingress {
        description      = "Tomcat access to instances"
        from_port        = 8080
        to_port          = 8080
        protocol         = "tcp"
        cidr_blocks      = ["0.0.0.0/0"]
    }

    egress {
        from_port        = 0
        to_port          = 0
        protocol         = "-1"
        cidr_blocks      = ["0.0.0.0/0"]
        ipv6_cidr_blocks = ["::/0"]
    }

    tags = {
        Name = "HTTP"
    }
}

resource "aws_security_group" "appdynamics_access" {
    provider            = aws.us-east-1
    name                = "appdynamics_access"
    description         = "Allow access to appdynamics from application instances"
    vpc_id              = "vpc-03ca1ab2a269d2df6" #"${aws_vpc.internet_access_vpc.id}"
    
    ingress {
        description     = "access to appdynamics"
        from_port       = 8000
        to_port         = 8000
        protocol        = "tcp"
        cidr_blocks     = ["0.0.0.0/0"]
    }

    egress {
        from_port        = 0
        to_port          = 0
        protocol         = "-1"
        cidr_blocks      = ["0.0.0.0/0"]
        ipv6_cidr_blocks = ["::/0"]
    }

    tags = {
        Name = "AppDynamics"
    }
}

resource "aws_security_group" "ssh_access-1" {
    provider            = aws.us-east-1
    name                = "ssh_access"
    description         = "Allow ssh  access"
    vpc_id              = "vpc-03ca1ab2a269d2df6" #"${aws_vpc.internet_access_vpc.id}"

    ingress {
        description      = "SSH access to instances"
        from_port        = 22
        to_port          = 22
        protocol         = "tcp"
        cidr_blocks      = "${var.cdirs_acesso_remoto}"
    }

    egress {
        from_port        = 0
        to_port          = 0
        protocol         = "-1"
        cidr_blocks      = ["0.0.0.0/0"]
        ipv6_cidr_blocks = ["::/0"]
    }

    tags = {
        Name = "SSH"
    }
}