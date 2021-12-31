import { spawn } from 'child_process';
function exec(serviceName, command) {
    console.log(`Started service [${serviceName}]`);
    let cmd = spawn(command, [], { cwd: './' + serviceName, shell: true });
    cmd.stdout.on('data', function (data) {
        process.stdout.write(`[${serviceName}] ${data}`);
    });
    cmd.stderr.on('data', function (data) {
        process.stderr.write(`[${serviceName}] ${data}`);
    });
    return cmd;
}
const services = new Map();
services.set('planner', exec('planner', 'sh mvnw spring-boot:run'));
services.set('weatherservice', exec('weatherservice', 'node src/server.js'));
services.set('toposervice', exec('toposervice', 'sh mvnw spring-boot:run'));
services.set('server', exec('server', 'node src/server.js'));
process.on('SIGINT', async () => {
    for (var [name, cmd] of services) {
        console.log(`Killing service [${name}]`);
        cmd.stdin.pause();
        await cmd.kill();
    }
    process.exit();
});