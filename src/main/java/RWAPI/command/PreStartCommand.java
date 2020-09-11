package RWAPI.command;

import java.util.ArrayList;
import java.util.List;

import RWAPI.Character.shop.entity.EntityMerchant;
import RWAPI.main;
import RWAPI.Character.PlayerData;
import RWAPI.game.Game;
import RWAPI.util.GameStatus;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;

public class PreStartCommand implements ICommand {

	@Override
	public int compareTo(ICommand o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		return "StartCommand";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "테스트";
	}

	@Override
	public List<String> getAliases() {
		List<String> aliase = new ArrayList();
		aliase.add("rwpready");
		return aliase;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

		if(main.game != null){
			main.game.GameTimer.reset();
		}
		main.game = new Game();
		main.game.start = GameStatus.READY;
		server.getWorld(0).getGameRules().setOrCreateGameRule("disableElytraMovementCheck", "true");
		List<EntityPlayerMP> players = server.getPlayerList().getPlayers();
		for(EntityPlayerMP player : players) {
			main.game.addplayerList(new PlayerData(player),player.getUniqueID());
		}
		main.game.readyPlayer();
		server.getPlayerList().sendMessage(new TextComponentString("게임 준비중입니다."));
		server.getPlayerList().sendMessage(new TextComponentString("게임에 참가하실 분들은 '/y'를 입력해 주시기 바랍니다."));
		server.getPlayerList().sendMessage(new TextComponentString("게임 참가 인원  : "));
		if(main.game.GameTimer != null) {
			MinecraftForge.EVENT_BUS.register(main.game.GameTimer);
			main.game.GameTimer = null;
		}
		for(EntityPlayerMP player : players) {
			server.getPlayerList().sendMessage(new TextComponentString(player.getName()));
		}
		/*EntityMerchant en = new EntityMerchant(server.getWorld(0));
		en.posX = -62;
		en.posY = 53;
		en.posZ = 109;
		//en.attemptTeleport(-62,53,109);
		server.getWorld(0).spawnEntity(en);*/
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		// TODO Auto-generated method stub
		return false;
	}

}
