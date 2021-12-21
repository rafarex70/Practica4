import { spawnSync } from 'child_process';
function exec(serviceName, command) {
    console.log(`Starting docker image for [${serviceName}]`);
    console.log(`Command: ${command}`);
    spawnSync(command, [], {
        shell: true,
        stdio: 'inherit'
    });
}
exec('MongoDB', 'docker run --rm -d -p 27017-27019:27017-27019 --name mongodb mongo');
exec('MySQL', 'docker run --rm -d -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_DATABASE=eoloplantsDB -p 3306:3306 --name mysql mysql');
exec('Kaftka', 'docker run --rm --net=host -e ADV_HOST="127.0.0.1" -p 2181:2181 -p 3030:3030 -p 9092:9092 lensesio/fast-data-dev');