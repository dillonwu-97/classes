// Server program 
const express = require('express')
const { createReadStream } = require('fs')
const bodyParser = require('body-parser')
const cookieParser = require('cookie-parser')
const {randomBytes} = require('crypto')

const app = express()

const USERS = {
	alice: 'password',
	bob: 'hunter2'
}
const BALANCES = {
	alice: 500,
	bob: 100
}
// sessionid -> username
const SESSIONS = {

}


const COOKIE_SECRET = 'asdlfkjcxzvewrahbvxc'
// let nextSessionId = 0

app.use(bodyParser.urlencoded({ extended: false }))
app.use(cookieParser(COOKIE_SECRET))

app.get('/', (req, res) => {
	// const username = req.signedCookies.username
	const sessionId = req.cookies.sessionId
	const username = SESSIONS[sessionId]

	if (username) {
		const balance = BALANCES[username]
		res.send(`Hi ${username} ! You have ${balance},
			<form method='POST' action='/transfer'>
				Send amount:
				<input name='amount' />
				To user:
				<input name='to' />
				<input type='submit' value='Send' />
			</form>
		`)
	} else {
		createReadStream('index.html').pipe(res)
	}
})

app.post('/login', (req, res) => {
	const username = req.body.username
	const password = USERS[username]
	// console.log(req.body)
	if (req.body.password === password) {
		// res.cookie('username', username, {signed: true})
		// 16 bytes = 128 bits of entropy
		const nextSessionId = randomBytes(16).toString('base64')
		// res.cookie('sessionId', nextSessionId)
		// in the demo, need to create a site like attacker.com and map to localhost and it will work
		// it wont work normally because localhost is recognized as the same domain
		res.cookie('sessionId', nextSessionId, {
			// secure: true, // cant do this on localhost so just http server
			httpOnly: true, // protect from javascript
			sameSite: 'lax', // protect form xsrf
			maxAge: 30 * 24 * 60 * 1000 // 30 days
		})
		SESSIONS[nextSessionId] = username
		// nextSessionId +=1
		res.redirect('/')
	} else {
		res.send('fail')
	}
})

app.post('/transfer', (req, res) => {
	/*
	const to = req.body.to
	const fr = req.cookies.sessionId
	const amount = req.body.amount
	BALANCES[to] += parseInt(amount)
	BALANCES[ SESSIONS[fr] ] -= parseInt(amount)
	res.redirect('/')
	*/

	const sessionId = req.cookies.sessionId
	const username = SESSIONS[sessionId]
	if (!username) {
		res.send('fail!')
		return
	}

	const amount = Number(req.body.amount)
	const to = req.body.to

	BALANCES[username] -= amount
	BALANCES[to] += amount

	res.redirect('/')
})

app.get('/logout', (req, res) => {
	// res.clearCookie('username')
	const sessionId = req.cookies.sessionId
	delete SESSIONS[sessionId]
	// res.clearCookie('sessionId')
	res.clearCookie('sessionId', {
		// secure:true, 
		httpOnly:true,
		sameSite: 'lax'
	})
	res.redirect('/')
})


// load homepage

app.listen(4000)