output ips-blocking {
  value       = "${aws_instance.prd-blocking-application[*].public_ip}"
  sensitive   = false
  description = "description"
  depends_on  = []
}

output ips-reactive {
  value       = "${aws_instance.prd-reactive-application[*].public_ip}"
  sensitive   = false
  description = "description"
  depends_on  = []
}