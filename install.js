import { spawnSync } from 'child_process';
function exec(serviceName, command) {
    console.log(`Installing dependencies for [${serviceName}]`);
    console.log(`Folder: ${serviceName} Command: ${command}`);
    spawnSync(command, [], {
        cwd: serviceName,
        shell: true,
        stdio: 'inherit'
    });
}
exec('weatherservice', 'npm install');
exec('toposervice', 'sh mvnw install');
exec('server', 'npm install');