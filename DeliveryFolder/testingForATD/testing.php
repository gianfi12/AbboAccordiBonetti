    /*
    test a correct signup
    */
    public function testSignup() : void {
        $_SERVER["REQUEST_METHOD"] = "POST";
        unset($_POST);
        $_POST["username"] = "newUserForTest";
        $_POST["password"] = "test";
        $_POST["firstName"] = "newUserForTest";
        $_POST["lastName"] = "lastname";
        $_POST["email"] = "m@m.it";
        $_POST["fiscalCode"] = "CFRTGVHYBUJNHYTG";
        $_POST["documentPhoto"] = "\"dummyphoto\"";
        $response = json_decode(executePHP(__DIR__ . "/../accounts/signup/index.php"));

        $this->assertEquals($response->result, 200);
        $this->assertEquals(Accounts::userFiscalCode("newUserForTest"), "CFRTGVHYBUJNHYTG");
        $this->assertTrue(is_file( __DIR__ . "/../userDocumentPhotos/CFRTGVHYBUJNHYTG.jpg"));
    }

    /*
    test a signup with a username already present
    */
    public function testSignupWithAlreadyPresentUsername() : void {
        $_SERVER["REQUEST_METHOD"] = "POST";
        unset($_POST);
        $_POST["username"] = "userForTest";
        $_POST["password"] = "test";
        $_POST["firstName"] = "NameDuplicated";
        $_POST["lastName"] = "lastname";
        $_POST["email"] = "m@m.it";
        $_POST["fiscalCode"] = "CFRTGVHYBUJNHYTG";
        $_POST["documentPhoto"] = "\"dummyphoto\"";
        $response = json_decode(executePHP(__DIR__ . "/../accounts/signup/index.php"));

        $this->assertEquals(405,$response->result);
    }

    /*
    test a login with a username not present
    */
    public function testLoginWithWrongUsername(): void
    {
        $_SERVER["REQUEST_METHOD"] = "POST";
        unset($_POST);
        $_POST["username"] = "wrongUser";
        $_POST["password"] = "wrongPassword";
        $response = json_decode(executePHP(__DIR__ . "/../accounts/login/index.php"));

        $this->assertEquals(401, $response->result);
    }

    /*
    test a correct login 
    */
    public function testLoginWithCorrectUsername(): void
    {
        $_SERVER["REQUEST_METHOD"] = "POST";
        unset($_POST);
        $_POST["username"] = "regularUser";
        $_POST["password"] = "test";
        $response = json_decode(executePHP(__DIR__ . "/../accounts/login/index.php"));

        $this->assertEquals(200,$response->result);
    }

    /*
    test a login with a correct username but a wrong password
    */
    public function testLoginWithWrongPassword(): void
    {
        $_SERVER["REQUEST_METHOD"] = "POST";
        unset($_POST);
        $_POST["username"] = "regularUser";
        $_POST["password"] = "testWrong";
        $response = json_decode(executePHP(__DIR__ . "/../accounts/login/index.php"));

        $this->assertEquals(401,$response->result);
    }

    /*
    test a login with a username of a not accepted yet user
    */
    public function testLoginFromAUserNotAccepted(): void
    {
        $_SERVER["REQUEST_METHOD"] = "POST";
        unset($_POST);
        $_POST["username"] = "userForTest";
        $_POST["password"] = "test";
        $response = json_decode(executePHP(__DIR__ . "/../accounts/login/index.php"));

        $this->assertEquals(403,$response->result);
    }

    /*
    test a login with a username of a suspended user
    */
    public function testLoginFromASuspendedUser(): void
    {
        $_SERVER["REQUEST_METHOD"] = "POST";
        unset($_POST);
        $_POST["username"] = "suspendedUser";
        $_POST["password"] = "test";
        $response = json_decode(executePHP(__DIR__ . "/../accounts/login/index.php"));

        $this->assertEquals(402,$response->result);
    }

    /*
    test that an officer can make a report
    */
    public function testOfficerSendReport(): void
    {
        $_SERVER["REQUEST_METHOD"] = "POST";
        unset($_GET);
        unset($_POST);
        $_POST["username"] = "officerUser";
        $_POST["password"] = "test";
        $_POST["plate"] = "AA111AA";
        $_POST["violationType"] = "1";
        $_POST["latitude"] = 45.4312;
        $_POST["longitude"] = 9.12584;
        $_POST["pictures"] = "[\"dummypicture\", \"dummypicture\"]";
        $response = json_decode(executePHP(DIR . "/../mobile/reports/index.php"));

        $this->assertEquals($response->result, 200);

    }


    /*
    test that the user can get its own past reports
    */
    public function testGetReports(): void
    {
        $_SERVER["REQUEST_METHOD"] = "GET";
        unset($_GET);
        $_GET["username"] = "regularUser";
        $_GET["password"] = "test";
        $response = json_decode(executePHP(DIR . "/../mobile/reports/index.php"));

        $this->assertEquals($response->result, 200);
    }