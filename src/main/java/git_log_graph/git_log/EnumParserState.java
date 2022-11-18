package git_log_graph.git_log;

enum EnumParserState {
	WAITING_FOR_NEW_COMMIT, WAITING_FOR_MESSAGE, READING_MESSAGE, 
	
	/**
	 * [2022-01-23] Support GPGSIG header
	 * <pre>
commit 23d20d81150deef9069b70f5aca08d73664d7668
tree 8fe179e247851c926c9c8526c7401bf028d226c6
parent aae0fd293bf5d9dbb9b8e9428e967239d5d84e2e
author SM Chan <jpeechan@gmail.com> 1607469684 -0500
committer GitHub <noreply@github.com> 1607469684 -0500
gpgsig -----BEGIN PGP SIGNATURE-----
 
 wsBcBAABCAAQBQJf0Ap0CRBK7hj4Ov3rIwAAdHIIADvRLtF0gw2oscvMiAKU1Amp
 jmUz7uaatBu4nhcGzrSJri3izJRzfPakAjL5IdYTqLOef0/5VK4XjKF15ZdkBEcx
 q0wge09B0PyjzomXM1Otj8m+GpeCxEEgPpMRDYNB/dLFRKDQY9c/yEIpw1VtU0ty
 2K469e2bkOJPa10aSsJv5xYU0a/nU6g3XXQ3lCeWw1aMQPXVjPJB2qIT++ofTgvJ
 UNzc83EcKbXOouFAIGr+SRus1NMllScbps+SGtnj0xOR/dlhjEFvFXW+/VfM0gP6
 x0YzUSwQQm5eTqeR5kZ3F1LPp8XpnhePSCYU4YwzGuYlyUGDdcKXtWdmEByMirg=
 =mPFh
 -----END PGP SIGNATURE-----
 

    Update .github/workflows/pio-build.yml
    
    Co-authored-by: Fernando Galandrini <fernando.galandrini@gmail.com>

     * </pre>
     */
	READING_GPGSIG
}
