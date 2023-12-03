const express = require("express");
const mysql = require("mysql");
const app = express();
const port = 3000;

// MySQL 연결 정보 설정
const connection = mysql.createConnection({
  host: "localhost", // MySQL 호스트
  user: "root", // MySQL 사용자 이름
  password: "root", // MySQL 비밀번호
  database: "tetris", // 사용할 데이터베이스 이름
});

// MySQL 연결
connection.connect((err) => {
  if (err) {
    console.error("MySQL 연결 실패:", err);
    throw err;
  }
  console.log("MySQL 연결 성공");
});

// Express 미들웨어 설정
app.use(express.json());

// 회원가입 엔드포인트
app.post("/signup", (req, res) => {
  const { id, password } = req.body;

  const query = `INSERT INTO user (id, password) VALUES (?, ?)`;

  connection.query(query, [id, password], (err, result) => {
    if (err) {
      console.error("회원가입 실패:", err);
      res.status(500).json({ message: "회원가입 실패" });
    } else {
      res.status(201).json({ message: "회원가입 성공" });
    }
  });
});

// 로그인 엔드포인트
app.post("/login", (req, res) => {
  const { id, password } = req.body;

  // 사용자가 입력한 ID와 Password를 사용하여 데이터베이스에서 검사
  const loginQuery = "SELECT * FROM user WHERE id = ? AND password = ?";
  connection.query(loginQuery, [id, password], (err, result) => {
    if (err) {
      console.error("로그인 실패:", err);
      res.status(500).json({ message: "로그인 실패" });
    } else {
      if (result.length > 0) {
        res.status(200).json({ message: "로그인 성공" });
      } else {
        res
          .status(401)
          .json({
            message: "로그인 실패 - 아이디 또는 비밀번호가 일치하지 않음",
          });
      }
    }
  });
});

// 중복 ID 확인 엔드포인트
app.post("/checkDuplicate", (req, res) => {
  const { id } = req.body;

  // 중복 ID 검사를 수행
  const checkQuery = "SELECT id FROM user WHERE id = ?";
  connection.query(checkQuery, [id], (checkErr, checkResult) => {
    if (checkErr) {
      console.error("ID 중복 확인 실패:", checkErr);
      res.status(500).json({ message: "ID 중복 확인 실패" });
    } else if (checkResult.length > 0) {
      // 중복 ID가 이미 존재하는 경우
      res.status(200).json({ message: "중복된 ID입니다" });
    } else {
      // 중복이 없는 경우
      res.status(204).json({ message: "사용 가능한 ID입니다" });
    }
  });
});

app.get("/level", (req, res) => {
  const id = req.query.id; // URL 쿼리 스트링에서 ID 추출
  const levelQuery = "SELECT level FROM user WHERE id = ?"; // SQL 쿼리 수정
  connection.query(levelQuery, [id], (err, result) => {
    if (err) {
      console.error("레벨 조회 실패:", err);
      res.status(500).json({ message: "레벨 조회 실패" });
    } else {
      res.status(200).json(result[0]); // 결과 배열의 첫 번째 요소 반환
    }
  });
});

app.post("/level", (req, res) => {
  const userId = req.body.user_id; // extract from request body instead of query string
  const level = req.body.level; // extract 'level' from request body

  const updateQuery = "UPDATE user SET level=? WHERE id=?";

  connection.query(updateQuery, [level, userId], (err, result) => {
    if (err) {
      console.error("레벨 업데이트 실패:", err);
      res.status(500).json({ message: "레벨 업데이트 실패" });
    } else {
      res.status(200).json({ message: "레벨 업데이트 성공" });
    }
  });
});

app.get("/exp", (req, res) => {
  const id = req.query.id; // URL 쿼리 스트링에서 ID 추출
  const expQuery = "SELECT exp FROM user WHERE id = ?"; // SQL 쿼리 수정
  connection.query(expQuery, [id], (err, result) => {
    if (err) {
      console.error("경험치 조회 실패:", err);
      res.status(500).json({ message: "경험치 조회 실패" });
    } else {
      res.status(200).json(result[0]);
      {
        message: result[0];
      } // 결과 배열의 첫 번째 요소 반환
    }
  });
});

app.get("/itemReserves", (req, res) => {
  const id = req.query.id; // URL 쿼리 스트링에서 ID 추출
  const expQuery = "SELECT item_reserves FROM user WHERE id = ?"; // SQL 쿼리 수정
  connection.query(expQuery, [id], (err, result) => {
    if (err) {
      console.error("아이템 조회 실패:", err);
      res.status(500).json({ message: "아이템 조회 실패" });
    } else {
      res.status(200).json(result[0]); // 결과 배열의 첫 번째 요소 반환
    }
  });
});

app.post("/exp", (req, res) => {
  const userId = req.body.user_id;
  const exp = req.body.exp;

  const updateQuery = "UPDATE user SET exp=? WHERE id=?";

  connection.query(updateQuery, [exp, userId], (err, result) => {
    if (err) {
      console.error("경험치 업데이트 실패:", err);
      res.status(500).json({ message: "경험치 업데이트 실패" });
    } else {
      res.status(200).json({ message: "경험치 업데이트 성공" });
    }
  });
});

app.post("/itemReserve", (req, res) => {
  const userId = req.body.user_id;
  const itemReserve = req.body.itemReserves;

  const updateQuery = "UPDATE user SET item_reserves=? WHERE id=?";

  connection.query(updateQuery, [itemReserve, userId], (err, result) => {
    if (err) {
      console.error("아이템 업데이트 실패:", err);
      res.status(500).json({ message: "아이템 업데이트 실패" });
    } else {
      res.status(200).json({ message: "아이템 업데이트 성공" });
    }
  });
});

// Score 업데이트 엔드포인트
app.post("/score", (req, res) => {
  const { user_id, score, mode } = req.body;

  // 만약 점수가 0보다 큰 경우에만 데이터를 추가
  if (score > 0) {
    // 사용자 ID, 점수, 모드를 Score 테이블에 저장
    const insertScoreQuery =
      "INSERT INTO score (user_id, score, mode) VALUES (?, ?, ?)";
    connection.query(
      insertScoreQuery,
      [user_id, score, mode],
      (err, result) => {
        if (err) {
          console.error("스코어 업데이트 실패:", err);
          res.status(500).json({ message: "스코어 업데이트 실패" });
        } else {
          res.status(201).json({ message: "스코어 업데이트 성공" });
        }
      }
    );
  } else {
    // 점수가 0 이하인 경우 데이터를 추가하지 않음
    res.status(400).json({ message: "점수가 0 이하입니다" });
  }
});

// 랭킹 조회 엔드포인트
app.get("/ranking", (req, res) => {
  const { mode } = req.query; // 클라이언트에서 mode를 쿼리 파라미터로 받음

  // 모드에 따라 랭킹을 조회
  let rankingQuery;
  if (mode) {
    // 모드가 제공되면 해당 모드의 랭킹을 조회
    rankingQuery =
      "SELECT user_id, MAX(score) AS max_score FROM Score WHERE mode = ? GROUP BY user_id ORDER BY max_score DESC";
    connection.query(rankingQuery, [mode], (err, result) => {
      if (err) {
        console.error("모드별 랭킹 조회 실패:", err);
        res.status(500).json({ message: "모드별 랭킹 조회 실패" });
      } else {
        res.status(200).json(result);
      }
    });
  } else {
    // 모드가 제공되지 않으면 전체 랭킹을 조회
    rankingQuery =
      "SELECT user_id, MAX(score) AS max_score FROM Score GROUP BY user_id ORDER BY max_score DESC";
    connection.query(rankingQuery, (err, result) => {
      if (err) {
        console.error("랭킹 조회 실패:", err);
        res.status(500).json({ message: "랭킹 조회 실패" });
      } else {
        res.status(200).json(result);
      }
    });
  }
});

// showPanelMaxScore 엔드포인트
app.get("/showPanelMaxScore", (req, res) => {
  const { user_id } = req.query; // 클라이언트에서 user_id를 쿼리 파라미터로 받음

  // 사용자의 최고 점수를 조회
  const maxScoreQuery =
    "SELECT MAX(score) AS max_score FROM score WHERE user_id = ?";
  connection.query(maxScoreQuery, [user_id], (err, result) => {
    if (err) {
      console.error("최고 점수 조회 실패:", err);
      res.status(500).json({ message: "최고 점수 조회 실패" });
    } else {
      if (result[0].max_score !== null) {
        res.status(200).json({ max_score: result[0].max_score });
      } else {
        res.status(404).json({ message: "사용자의 최고 점수가 없음" });
      }
    }
  });
});

app.get("/", (req, res) => {
  res.send("Hello, World!");
});

app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});
